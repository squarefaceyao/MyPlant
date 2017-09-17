package com.songc.service.imp;

import com.songc.dao.imp.HbaseDao;
import com.songc.entity.HbaseFile;
import com.songc.service.HbaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HbaseServiceImpTest {

    @Autowired
    private HbaseService hbaseService;

    @MockBean
    private HbaseDao hbaseDao;

    @Test
    public void save() throws Exception {
        Long id = 100L;
        HbaseFile hbaseFile = new HbaseFile();
        hbaseFile.setParentId(id);
        given(hbaseDao.save(hbaseFile)).willReturn(hbaseFile);
        HbaseFile hbaseFile1 = hbaseService.save(hbaseFile);
        assertEquals(id, hbaseFile1.getParentId());
    }

    @Test
    public void find() throws Exception {
        String rowKey = "songc";
        Long id = 100L;
        HbaseFile hbaseFile = new HbaseFile();
        hbaseFile.setParentId(id);
        given(hbaseDao.find(rowKey)).willReturn(hbaseFile);
        HbaseFile hbaseFile1 = hbaseService.find(rowKey);
        assertEquals(id, hbaseFile1.getParentId());
    }

    @Test
    public void findByParentId() throws Exception {
        Long id = 100L;
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        HbaseFile hbaseFile = new HbaseFile();
        hbaseFile.setParentId(id);
        hbaseFiles.add(hbaseFile);
        given(hbaseDao.findByParentId(id)).willReturn(hbaseFiles);
        List<HbaseFile> hbaseFiles1 = hbaseService.findByParentId(id);
        assertEquals(1, hbaseFiles1.size());
        assertEquals(id, hbaseFiles1.get(0).getParentId());
    }

    @Test
    public void delete() throws Exception {
        String rowKey = "songc";
        hbaseService.delete(rowKey);
        verify(hbaseDao).delete(rowKey);
    }

    @Test
    public void deleteByParentId() throws Exception {
        Long id = 100L;
        hbaseService.deleteByParentId(id);
        verify(hbaseDao).deleteByParentId(id);
    }

    @Test
    public void save1() throws Exception {
        Long id = 100L;
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        HbaseFile hbaseFile = mock(HbaseFile.class);
        hbaseFiles.add(hbaseFile);
        when(hbaseFile.getParentId()).thenReturn(id);
        given(hbaseDao.save(hbaseFiles)).willReturn(hbaseFiles);
        List<HbaseFile> hbaseFiles1 = hbaseService.save(hbaseFiles);
        assertEquals(1, hbaseFiles1.size());
        assertEquals(id, hbaseFiles1.get(0).getParentId());
    }


//    @Test
//    public void addHbaseFile() throws Exception {
//        HbaseFile hbaseFile = new HbaseFile();
//        hbaseFile.setParentId(100L);
//        hbaseFile.setName("songc");
//        hbaseFile.setContent(Bytes.toBytes("content"));
//        HbaseFile hbaseFile1 = hbaseFileDao.save(hbaseFile);
//        assertNotNull(hbaseFile1);
//        assertEquals(hbaseFile1.getName(),"songc");
//    }
//
//    @Test
//    public void findAll() throws Exception {
//        List<HbaseFile> hbaseFileList;
//        HbaseFile hbaseFile = new HbaseFile();
//        hbaseFile.setParentId(100L);
//        hbaseFile.setName("songc");
//        hbaseFile.setContent(Bytes.toBytes("content"));
//        HbaseFile hbaseFile1 = hbaseFileDao.save(hbaseFile);
//        hbaseFileList = hbaseFileDao.findAll();
//        assertNotNull(hbaseFileList);
//        System.out.println(hbaseFileList.get(0).getName());
//    }

}