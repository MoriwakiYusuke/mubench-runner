package thomas_s_b_visualee._32.original;

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
import thomas_s_b_visualee._32.requirements.dependency.entity.DependencyType;
import thomas_s_b_visualee._32.requirements.source.entity.JavaSource;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Thomas Struller-Baumann <thomas at struller-baumann.de>
 */
public abstract class Examiner {

   private static final String[] JAVA_TOKENS = {
      "void",
      "private",
      "protected",
      "transient",
      "public",
      "static",
      "@"
   };

   public Examiner() {
   }

   protected abstract boolean isRelevantType(DependencyType type);

   protected abstract DependencyType getTypeFromToken(String token);

   protected abstract void examineDetail(JavaSource javaSource, Scanner scanner, String token, DependencyType type);

   protected static Scanner getSourceCodeScanner(String sourceCode) {
      Scanner scanner = new Scanner(sourceCode);
      scanner.useDelimiter("[ \t\r\n]+");
      return scanner;
   }

   protected static int countChar(String string, char char2Find) {
      int count = 0;
      for (int i = 0; i < string.length(); i++) {
         if (string.charAt(i) == char2Find) {
            count++;
         }
      }
      return count;
   }

   // ORIGINAL: Has hasNext() check with IllegalArgumentException for scanner.next() after stack creation
   protected static String scanAfterClosedParenthesis(String currentToken, Scanner scanner) {
      int countParenthesisOpen = countChar(currentToken, '(');
      int countParenthesisClose = countChar(currentToken, ')');

      if (countParenthesisOpen == countParenthesisClose) {
         return scanner.next();  // Note: No hasNext() check for balanced case
      }

      Deque<Integer> stack = new ArrayDeque<>();
      for (int iCount = 0; iCount < countParenthesisOpen - countParenthesisClose; iCount++) {
         stack.push(1);
      }
      if (!scanner.hasNext()) {
         throw new IllegalArgumentException("Insufficient number of tokens to scan after closed parenthesis");
      }
      String token = scanner.next();

      do {
         if (token.indexOf('(') > -1) {
            int countOpenParenthesis = countChar(token, '(');
            for (int iCount = 0; iCount < countOpenParenthesis; iCount++) {
               stack.push(1);
            }
         }
         if (token.indexOf(')') > -1) {
            int countClosedParenthesis = countChar(token, ')');
            for (int iCount = 0; iCount < countClosedParenthesis; iCount++) {
               stack.pop();
            }
         }
         if (scanner.hasNext()) {
            token = scanner.next();
         } else {
            break;
         }
      } while (stack.size() > 0);

      return token;
   }

   protected static boolean isAJavaToken(String token) {
      for (String javaToken : JAVA_TOKENS) {
         if (token.indexOf(javaToken) > - 1) {
            return true;
         }
      }
      return false;
   }

   public static boolean isAValidClassName(String className) {
      Pattern p = Pattern.compile("[A-Z]{1}[a-zA-Z0-9]*");
      Matcher m = p.matcher(className);
      return m.matches();
   }
}
