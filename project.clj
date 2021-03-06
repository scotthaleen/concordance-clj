(require '[clojure.string :as s]
         '[clojure.java.shell :refer [sh]])

(def VERSION "0.1.0")

(def short-hash
  (try
    (let [{:keys [out exit]}
          (sh "git" "rev-parse" "--short" "HEAD")]
      (if (= 0 exit) (s/trim out) ""))
    (catch Exception e
      (println "WARNING: error occured parsing git revision"))))


(defproject concordance-clj VERSION
  :description "concordance"
  :url "https://github.com/scotthaleen/concordance-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-beta2"]
                 [org.clojure/tools.cli "0.3.5"]
                 [instaparse "1.4.8"]]
  :main io.scotthaleen.concordance.app
  :target-path "target/%s"
  :jar-name ~(str
              "concordance-%s"
              (if (not-empty short-hash) (str "-" short-hash))
              ".jar")
  :profiles {
             :dev [:project/dev]
             :lint [:project/lint]
             :uberjar [:project/uberjar]
             :project/lint {:plugins [[jonase/eastwood "0.2.5"]]}
             :project/dev {
                           :dependencies [[reloaded.repl "0.2.3"]
                                          [org.clojure/tools.namespace "0.2.11"]

                                          [org.clojure/tools.nrepl "0.2.12"]
                                          [eftest "0.3.1"]
                                          [org.clojure/test.check "0.10.0-alpha2"]]
                           :source-paths ["dev"]
                           :jvm-opts ["-Dclojure.spec.check-asserts=true"]
                           :repl-options {:init-ns user}
                           }
             :project/uberjar {
                               :aot :all
                               :uberjar-name ~(str
                                               "concordance-%s"
                                               (if (not-empty short-hash) (str "-" short-hash))
                                               "-standalone.jar")}

             }
  :aliases {"lint" ["with-profile" "lint" "eastwood"]
            "travis-ci" ["do" ["clean"] ["test"] ["uberjar"]]})

