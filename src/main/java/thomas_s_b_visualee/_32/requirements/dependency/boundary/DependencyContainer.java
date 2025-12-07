package thomas_s_b_visualee._32.requirements.dependency.boundary;

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
import thomas_s_b_visualee._32.requirements.dependency.entity.Dependency;
import thomas_s_b_visualee._32.requirements.source.entity.JavaSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Thomas Struller-Baumann <thomas at struller-baumann.de>
 */
public final class DependencyContainer {

   private static final List<Dependency> dependencies = new ArrayList<>();

   private static class DependencyContainerHolder {
      private static final DependencyContainer INSTANCE = new DependencyContainer();
   }

   private DependencyContainer() {
   }

   public static DependencyContainer getInstance() {
      return DependencyContainerHolder.INSTANCE;
   }

   public void clear() {
      dependencies.clear();
   }

   public List<Dependency> getDependencies() {
      return dependencies;
   }

   public void add(Dependency dependency) {
      dependencies.add(dependency);
   }

   public void addDependency(Dependency dependency) {
      dependencies.add(dependency);
   }

   public Set<Dependency> findAllDependencyTo(JavaSource javaSource) {
      Set<Dependency> dependencyList = new HashSet<>();
      for (Dependency dependency : dependencies) {
         if (dependency.getJavaSourceTo().equals(javaSource)) {
            dependencyList.add(dependency);
         }
      }
      return dependencyList;
   }

   public Set<Dependency> findAllDependencyFrom(JavaSource javaSource) {
      Set<Dependency> dependencyList = new HashSet<>();
      for (Dependency dependency : dependencies) {
         if (dependency.getJavaSourceFrom().equals(javaSource)) {
            dependencyList.add(dependency);
         }
      }
      return dependencyList;
   }
}
