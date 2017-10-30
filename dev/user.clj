(ns ^:no-doc user
  (:require
   [clojure.repl :refer :all]
   [clojure.java.io :as io]
   [clojure.pprint :refer [pprint]]
   [clojure.tools.namespace.repl :as tools]
   [eftest.runner :as eftest]
   [clojure.spec.alpha :as s]
   [io.scotthaleen.concordance.core]))


(ns-unmap *ns* 'test)


(defn test []
  (eftest/run-tests (eftest/find-tests "test") {:multithread? false}))

(when (io/resource "local.clj")
  (load "local"))

(defn refresh []
  (tools/refresh))

(defn refresh-all []
  (tools/refresh-all))


