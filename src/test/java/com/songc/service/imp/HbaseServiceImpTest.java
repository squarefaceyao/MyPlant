package com.songc.service.imp;

import com.songc.dao.HbaseFileDao;
import com.songc.entity.HbaseFile;
import com.songc.util.HbaseFileUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HbaseServiceImpTest {

    @Autowired
    private HbaseFileUtil hbaseFileUtil;

    @Autowired
    private HbaseFileDao hbaseFileDao;

    @Test
    public void addHbaseFile() throws Exception {
        hbaseFileUtil.initialize();
        HbaseFile hbaseFile = hbaseFileDao.save(100L, "songc", Bytes.toBytes("12345678979"));
        assertNotNull(hbaseFile);
        assertEquals(hbaseFile.getName(),"songc");
    }

    @Test
    public void findAll() throws Exception {
        hbaseFileUtil.initialize();
        List<HbaseFile> hbaseFileList = hbaseFileDao.findAll();
//        assertNull(hbaseFileList);
        HbaseFile hbaseFile = hbaseFileDao.save(100L, "songc", Bytes.toBytes("12345678979"));
        hbaseFileList = hbaseFileDao.findAll();
        assertNotNull(hbaseFileList);
    }

}