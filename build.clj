(ns build
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]))

(def lib 'com.github.oelrich/julian-now)
(def version (format "0.9.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-%s.jar" (name lib) version))

(defn ^:export clean [_]
  (b/delete {:path "target"}))

(defn ^:export jar [_]
  (clean nil)
  (b/write-pom {:basis basis
                :lib lib
                :version version
                :scm {:url "https://github.com/oelrich/julian-now"}
                :src-dirs ["src"]
                :class-dir class-dir})
  (b/copy-dir {:src-dirs ["src"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file}))

(defn ^:export install [_]
  (b/install {:basis basis
              :lib lib
              :version version
              :jar-file jar-file
              :class-dir class-dir}))


(defn ^:export deploy [_]
  (dd/deploy {:installer :remote
              :artifact jar-file
              :pom-file
              (b/pom-path {:lib lib
                           :class-dir class-dir})}))