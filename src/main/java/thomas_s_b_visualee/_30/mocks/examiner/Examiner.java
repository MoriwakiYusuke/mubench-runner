package thomas_s_b_visualee._30.mocks.examiner;

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
import thomas_s_b_visualee._30.requirements.dependency.entity.DependencyType;
import thomas_s_b_visualee._30.requirements.source.entity.JavaSource;
import java.util.Scanner;

/**
 * Base Examiner class for requirements.
 * This is a stub class that provides the interface needed by JavaSourceInspector.
 *
 * @author Thomas Struller-Baumann <thomas at struller-baumann.de>
 */
public abstract class Examiner {

   public Examiner() {
   }

   protected abstract boolean isRelevantType(DependencyType type);

   public abstract DependencyType getTypeFromToken(String token);

   protected abstract void examineDetail(JavaSource javaSource, Scanner scanner, String token, DependencyType type);

   public abstract void examine(JavaSource javaSource);

   protected static Scanner getSourceCodeScanner(String sourceCode) {
      Scanner scanner = new Scanner(sourceCode);
      scanner.useDelimiter("[ \t\r\n]+");
      return scanner;
   }

   public static void findAndSetPackage(JavaSource javaSource) {
      Scanner scanner = Examiner.getSourceCodeScanner(javaSource.getSourceCode());
      while (scanner.hasNext()) {
         String token = scanner.next();
         if (javaSource.getPackagePath() == null && token.equals("package")) {
            if (!scanner.hasNext()) {
               break;
            }
            token = scanner.next();
            if (token.endsWith(";")) {
               String packagePath = token.substring(0, token.indexOf(';'));
               javaSource.setPackagePath(packagePath);
            }
         }
      }
   }
}
