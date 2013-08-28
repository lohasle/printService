/**
 * 
 */
package me.huijin.printService.commons;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author 付乐
 * @createTime 2013-8-22
 */
public class ResourceLoadUtilTest {
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
    
    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Test method for {@link me.huijin.printService.commons.ResourceLoadUtil#loadPropertiesFromXML(java.lang.String, java.lang.Class)}.
     * @throws IOException 
     */
    @Test
    public final void testLoadPropertiesFromXML() throws IOException {
        Properties pro = ResourceLoadUtil.loadPropertiesFromXML("/resources.xml", ResourceLoadUtilTest.class);
        System.out.println(pro.getProperty("acrobat.exe"));
        System.out.println(pro.getProperty("acroRd32.exe"));
    }
    
}
