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

package org.apache.james.mime4j.decoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

public class Base64OutputStreamTest extends TestCase {

    public void testEncode() throws IOException {
        ByteArrayOutputStream bos = null;
        Base64OutputStream encoder = null;
        
        /*
         * Simple initial test.
         */
        bos = new ByteArrayOutputStream();
        encoder = new Base64OutputStream(bos);
        encoder.write(fromString("This is the plain text message!"));
        encoder.close();
        assertEquals("VGhpcyBpcyB0aGUgcGxhaW4gdGV4dCBtZXNzYWdlIQ==\r\n", toString(bos.toByteArray()));
    }

    public void testEncodeUnderlyingStreamStaysOpen() throws IOException {
        ByteArrayOutputStream bos = null;
        Base64OutputStream encoder = null;
        
        bos = new ByteArrayOutputStream();
        encoder = new Base64OutputStream(bos);
        encoder.write(fromString("This is the plain text message!"));
        encoder.close();

        try {
            encoder.write('b');
            fail();
        } catch (IOException expected) {
        }
        
        bos.write('y');
        bos.write('a');
        bos.write('d');
        bos.write('a');
        assertEquals("VGhpcyBpcyB0aGUgcGxhaW4gdGV4dCBtZXNzYWdlIQ==\r\nyada", toString(bos.toByteArray()));
    }
        
    private byte[] fromString(String s) {
        try {
            return s.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            fail(e.getMessage());
            return null;
        }
    }
    
    private String toString(byte[] b) {
        try {
            return new String(b, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            fail(e.getMessage());
            return null;
        }
    }
}
