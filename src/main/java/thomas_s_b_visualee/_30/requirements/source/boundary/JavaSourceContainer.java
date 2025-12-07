package thomas_s_b_visualee._30.requirements.source.boundary;

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
import thomas_s_b_visualee._30.requirements.source.entity.JavaSource;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Thomas Struller-Baumann <thomas at struller-baumann.de>
 */
public class JavaSourceContainer {

   private static Set<JavaSource> javaSources = new HashSet<>();

   private JavaSourceContainer() {
   }

   public static void clear() {
      javaSources.clear();
   }

   public static Set<JavaSource> getJavaSources() {
      return javaSources;
   }

   public static void addJavaSource(JavaSource javaSource) {
      javaSources.add(javaSource);
   }

   public static JavaSource find(String name) {
      for (JavaSource javaSource : javaSources) {
         if (javaSource.getName().equals(name)) {
            return javaSource;
         }
      }
      return null;
   }
}
