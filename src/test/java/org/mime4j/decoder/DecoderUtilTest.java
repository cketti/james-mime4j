/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package org.mime4j.decoder;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;

/**
 * 
 *
 * 
 * @version $Id: DecoderUtilTest.java,v 1.3 2004/11/09 12:57:40 ntherning Exp $
 */
public class DecoderUtilTest extends TestCase {

    public void setUp() {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
    }
    /*
    public void testDecodeEncodedWords() {
        String s = "=?ISO-2022-JP?B?GyRCTCQbKEobJEI+NRsoShskQkJ6GyhKGyRCOS0bKEo=?= "
                 + "=?ISO-2022-JP?B?GyRCOXAbKEobJEIiKBsoShskQiU1GyhKGyRCJSQbKEo=?= "
                 + "=?ISO-2022-JP?B?GyRCJUkbKEobJEIlUxsoShskQiU4GyhKGyRCJU0bKEo=?= "  
                 + "=?ISO-2022-JP?B?GyRCJTkbKEobJEIkThsoShskQjdoGyhKGyRCRGobKEo=?= "
                 + "=?ISO-2022-JP?B?GyRCSEcbKEobJEIkRxsoShskQiQ5GyhKGyRCISobKEo=?=";      
        
        s = DecoderUtil.decodeEncodedWords(s);
        System.out.println(s);
    }*/
    
    public void testDecodeB() throws UnsupportedEncodingException {
        String s = DecoderUtil.decodeB("VGhpcyBpcyB0aGUgcGxhaW4gd"
                    + "GV4dCBtZXNzYWdlIQ==", "ISO8859-1");
        assertEquals("This is the plain text message!", s);
    }
    

    public void testDecodeQ() throws UnsupportedEncodingException {
        String s = DecoderUtil.decodeQ("=e1_=e2=09=E3_=E4_", 
                                                         "ISO8859-1");
        assertEquals("� �\t� � ", s);
    }
    
    public void testDecodeEncodedWords() {
        assertEquals("", DecoderUtil.decodeEncodedWords(""));
        assertEquals("Yada yada", DecoderUtil.decodeEncodedWords("Yada yada"));
        assertEquals("  ���\t�", 
                DecoderUtil.decodeEncodedWords("=?iso-8859-1?Q?_=20=e1=e2=E3=09=E4?="));
        assertEquals("Word 1 '  ��\t�'. Word 2 '  ��\t�'", 
                DecoderUtil.decodeEncodedWords("Word 1 '=?iso-8859-1?Q?_=20=e2=E3=09=E4?="
                        + "'. Word 2 '=?iso-8859-1?q?_=20=e2=E3=09=E4?='"));
        assertEquals("=?iso-8859-YADA?Q?_=20=t1=e2=E3=09=E4?=", 
                DecoderUtil.decodeEncodedWords("=?iso-8859-YADA?Q?_=20=t1=e2=E3=09=E4?="));
        assertEquals("A short text", 
                DecoderUtil.decodeEncodedWords("=?US-ASCII?B?QSBzaG9ydCB0ZXh0?="));
        assertEquals("A short text again!", 
                DecoderUtil.decodeEncodedWords("=?US-ASCII?b?QSBzaG9ydCB0ZXh0IGFnYWluIQ==?="));
        assertEquals("", DecoderUtil.decodeEncodedWords("=?iso8859-1?Q?="));
        assertEquals("", DecoderUtil.decodeEncodedWords("=?iso8859-1?b?="));
        assertEquals("", DecoderUtil.decodeEncodedWords("=?ISO-8859-1?Q?"));
        
        /*
         * Bug detected on June 7, 2005. Decoding the following string caused
         * OutOfMemoryError.
         */
        assertEquals("=3?!!\\=?\"!g6P\"!Xp:\"!", DecoderUtil.decodeEncodedWords("=3?!!\\=?\"!g6P\"!Xp:\"!"));
    }    
}
