(ns io.scotthaleen.concordance.app-test
  (:require [clojure.test :refer :all]
            [io.scotthaleen.concordance.app :as app]))



(def expected
  (str
   "a." \tab "hello" \tab "{1:1}" \newline
   "b." \tab "world" \tab "{1:1}" \newline \newline))


(deftest test-app
  (with-redefs [slurp (fn [f & opts] "Hello World!")]
    (testing "app run"
      (is
       (=
        expected
        (with-out-str
          (app/-main "dummy.txt")))))))


