(ns io.scotthaleen.concordance.tokenizer
  (:require
   [clojure.string :as sz :refer [lower-case]]
   [instaparse.core :as insta]))


(set! *warn-on-reflection* true)

(def ^{:private true} word-like-pattern "[^\\s\\.\\!\\?:;,\\-\"\\'\\(\\)\\[\\]]")


(def ^{:private true} ebnf-parser
  (insta/parser
   (str
   " <S> ::= text
     whitespace ::= #'\\s+'
     sentence_boundry ::= #'[\\.\\!\\?]' | #'$'
     seperator ::= #'[;|:|,|\\-]'
     <abbreviation> ::= #'([a-zA-Z0-9]\\.)+'
     <bracket> ::= #'[\\[\\]\\(\\)]'
     <contraction> ::= #'" word-like-pattern "+' <'\\''> #'" word-like-pattern "{0,2}'
     quote ::= #'[\"|\\']'
     <word> ::= #'" word-like-pattern "+'  | abbreviation | contraction
     sentence = (<quote> | <bracket> | <whitespace> | word | <seperator>)+ <sentence_boundry>+ <quote>*
     <text> ::= sentence <whitespace>* text?")))


(defn- ebnf-cleanse
  "normalize parser results to list of sentences with lowercase words"
  [sentence_result]
  (map lower-case (rest sentence_result)))


(defn ebnf-tokenizer
  [text]
  (map ebnf-cleanse (insta/parse ebnf-parser text)))


;;TODO naive whitespace

(def tokenizers
  {::ebnf-tokenizer ebnf-tokenizer
   ;;::naive-whitespace naive-whitespace
   ::default ebnf-tokenizer
   })
