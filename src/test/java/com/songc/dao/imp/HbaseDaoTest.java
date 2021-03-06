package com.songc.dao.imp;

import com.songc.config.HbaseConfiguration;
import com.songc.dto.HbaseFileDTO;
import com.songc.dto.HbaseFileWithContentDTO;
import com.songc.entity.HbaseFile;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HbaseDao.class, HbaseConfiguration.class})
@ActiveProfiles("test")
public class HbaseDaoTest {
    @Autowired
    private HbaseDao hbaseDao;
    private Long id = 100L;
    private String name = "songc";
    private byte[] content = "content".getBytes();

    @Test
    public void save() throws Exception {
        HbaseFile hbaseFile = new HbaseFile(id, name, content);
        hbaseDao.save(hbaseFile);
        List<HbaseFileDTO> hbaseFiles1 = hbaseDao.findByParentId(id);
        assertEquals(1, hbaseFiles1.size());
        assertEquals(id, hbaseFiles1.get(0).getParentId());
        assertEquals(name, hbaseFiles1.get(0).getName());
        List<HbaseFileWithContentDTO> hbaseFiles2 = hbaseDao.findContentByParentId(id);
        assertEquals(Arrays.toString(content), Arrays.toString(hbaseFiles2.get(0).getContent()));
    }

    @Test
    public void save1() throws Exception {
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            hbaseFiles.add(new HbaseFile(id, name, content));
        }
        hbaseDao.save(hbaseFiles);
        List<HbaseFileDTO> hbaseFiles1 = hbaseDao.findByParentId(id);
        assertEquals(10, hbaseFiles1.size());
        assertEquals(id, hbaseFiles1.get(0).getParentId());
        assertEquals(name, hbaseFiles1.get(0).getName());
        List<HbaseFileWithContentDTO> hbaseFiles2 = hbaseDao.findContentByParentId(id);
        assertEquals(10, hbaseFiles2.size());
        assertEquals(new String(content), new String(hbaseFiles2.get(0).getContent()));
    }

    @Test
    public void find() throws Exception {
        hbaseDao.save(new HbaseFile(id, name, content));
        List<byte[]> rowKeys = hbaseDao.getRowKeysByParentId(id);
        assertEquals(1, rowKeys.size());
        System.out.println(rowKeys.size());
        System.out.println(new String(rowKeys.get(0)));
        HbaseFileWithContentDTO hbaseFile = hbaseDao.find(new String(rowKeys.get(0)));
        assertEquals(new String(content), new String(hbaseFile.getContent()));
    }

    @Test
    public void findAll() throws Exception {
        List<HbaseFileDTO> files = hbaseDao.findAll();
        assertNotNull(files);
        System.out.println(files.size());
    }

    @Test
    public void findByParentId() throws Exception {
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            hbaseFiles.add(new HbaseFile(id, name, content));
        }
        hbaseDao.save(hbaseFiles);
        List<HbaseFileDTO> hbaseFiles1 = hbaseDao.findByParentId(id);
        assertEquals(10, hbaseFiles1.size());
    }

    @Test
    public void findContentByParentId() throws Exception {
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            hbaseFiles.add(new HbaseFile(id, name, content));
        }
        hbaseDao.save(hbaseFiles);
        List<HbaseFileWithContentDTO> hbaseFiles1 = hbaseDao.findContentByParentId(id);
        assertEquals(10, hbaseFiles1.size());
        assertEquals(new String(content), new String(hbaseFiles1.get(5).getContent()));
    }

    @Test
    public void delete() throws Exception {
        hbaseDao.save(new HbaseFile(id, name, content));
        List<byte[]> rowKeys = hbaseDao.getRowKeysByParentId(id);
        assertEquals(1, rowKeys.size());
        assertEquals(1, hbaseDao.findAll().size());
        hbaseDao.delete(new String(rowKeys.get(0)));
        assertEquals(0, hbaseDao.findAll().size());
    }

    @Test
    public void deleteByParentId() throws Exception {
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            hbaseFiles.add(new HbaseFile(id, name, content));
        }
        hbaseDao.save(hbaseFiles);
        assertEquals(10, hbaseDao.findAll().size());
        hbaseDao.deleteByParentId(id);
        assertEquals(0, hbaseDao.findAll().size());
    }

    @Test
    public void getRowKeysByParentId() throws Exception {
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            hbaseFiles.add(new HbaseFile(id, name, content));
        }
        hbaseDao.save(hbaseFiles);
        assertEquals(10, hbaseDao.getRowKeysByParentId(id).size());
    }

    @Test
    public void delete2() throws IOException {
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            hbaseFiles.add(new HbaseFile(id, name, content));
        }
        List<HbaseFile> newHbaseFiles = hbaseDao.save(hbaseFiles);
        assertEquals(10, hbaseDao.getRowKeysByParentId(id).size());
        List<String> rowKeyList = new ArrayList<>();
        for (HbaseFile newHbaseFile : newHbaseFiles) {
            rowKeyList.add(newHbaseFile.getRowKey());
        }
        hbaseDao.delete(rowKeyList);
        assertEquals(0, hbaseDao.getRowKeysByParentId(id).size());
    }

    @After
    public void after() throws IOException {
        hbaseDao.deleteByParentId(id);
    }

}