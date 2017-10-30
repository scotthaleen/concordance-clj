(ns io.scotthaleen.concordance.core-test
  (:require [clojure.test :refer :all]
            [io.scotthaleen.concordance.core :as core :refer :all])
  (:refer-clojure :exclude [frequencies]))


(def frequencies #'core/frequencies)

(deftest test-frequencies
  (testing "count and label with 1"
    (is
     (=
      {"a" '(1 1)
       "b" '(1)
       "c" '(1)}
      (frequencies 1 (list "a" "b" "a" "c")))))

  (testing "count and label with 99"
    (is
     (=
      {"foo" '(99 99 99)
       "bar" '(99 99)}
      (frequencies 99 (list "foo" "bar" "foo" "foo" "bar")))))

  (testing "empty sentence"
    (is
     (=
      {}
      (frequencies 5 (list)))))

  (testing "single word"
    (is
     (=
      {"a" '(5)}
      (frequencies 5 (list "a"))))))

