package thomas_s_b_visualee._30.requirements.resources;

/*
 * #%L
 * visualee
 * %%
 * Copyright (C) 2013 Thomas Struller-Baumann
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Struller-Baumann <thomas at struller-baumann.de>
 */
public class FileManager {

   private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());

   private FileManager() {
   }

   public static void copyFile(File source, File dest) throws IOException {
      InputStream is = null;
      OutputStream os = null;
      try {
         is = new FileInputStream(source);
         os = new FileOutputStream(dest);
         byte[] buffer = new byte[1024];
         int length;
         while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
         }
      } finally {
         is.close();
         os.close();
      }
   }

   public static File getFileFromResource(String resource) {
      URL url = FileManager.class.getResource(resource);
      File file = null;
      try {
         file = new File(url.toURI());
      } catch (URISyntaxException ex) {
         LOGGER.log(Level.SEVERE, null, ex);
      }
      return file;
   }

   public static String readFile(InputStream inputStream) throws IOException {
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
         while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
         }
      }
      return stringBuilder.toString();
   }
}
