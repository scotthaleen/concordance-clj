(ns io.scotthaleen.concordance.core
  (:require
   [clojure.string :refer [join]])
  (:refer-clojure :exclude [frequencies])
  (:gen-class))


(defn- frequencies
  ""
  [label sentence]
  (persistent!
   (reduce
    (fn [m word]
      (assoc! m word (cons label (get m word '()))))
    (transient {}) sentence)))


(defn generate-concordance
  "sorted list of concordance"
  [tokenizer text]
  (->>
   (reduce-kv
    (fn [res idx v]
      (merge-with
       concat
       res
       (frequencies (inc idx) v)))
    (sorted-map)
    (into [] (tokenizer text)))
   (into [])))


(defprotocol IConcordance
  (concordance [_] "return conordance"))

(defrecord Concordance [tokenizer text]
  IConcordance
  (concordance [_]
    (generate-concordance tokenizer text)))


(def ^{:private true} alphabet
  (map char (range (int \a) (inc (int \z)))))


(def ^{:private true} alphabetized
  ((fn f [i [letter & remaining :as letters]]
     (cons
      (apply str (repeat i letter))
      (lazy-seq
       (if-not (empty? remaining)
         (f i remaining)
         (f (inc i) alphabet)))))
   1 alphabet))


(defmethod clojure.core/print-method Concordance
  [c ^java.io.Writer w]
  (doseq [item (map
                (fn [id [k v]]
                  (format "%s.\t%s\t{%s:%s}\n" id k (count v) (join \, v)))
                alphabetized
                (concordance c))]
    (.write w item)))

