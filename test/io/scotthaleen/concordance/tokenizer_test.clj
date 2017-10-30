(ns io.scotthaleen.concordance.tokenizer-test
  (:require
   [io.scotthaleen.concordance.tokenizer :as tokenizer :refer [ebnf-tokenizer]]
   [clojure.test :refer :all]))


(deftest test-ebnf-parsing

  (testing "hello world"
    (is
     (= '(("hello" "world"))
        (ebnf-tokenizer "Hello World."))))

  (testing "commas"
    (is
     (= '(("hello" "world"))
        (ebnf-tokenizer "Hello, World."))))

  (testing "multiple sentences"
    (is
     (= '(("hello" "world") ("how" "are" "you" "world"))
        (ebnf-tokenizer "Hello, World. How are you world?"))))

  (testing "complex case sentences"
    ;https://www.ibm.com/developerworks/community/blogs/nlp/entry/tokenization?lang=en
    (is
     (= '(("i" "said" "what" "re" "you") ("crazy") ("said" "sandowsky") ("i" "can" "t" "afford" "to" "do" "that") ())
        (ebnf-tokenizer "\"I said, 'what're you? Crazy?'\" said Sandowsky. \"I can't afford to do that.\""))))

  (testing "hyphenation of words"
    (is
     (= '(("hello" "world"))
        (ebnf-tokenizer "Hello--World."))))

  (testing "contraction"
    (is
     (= '(("i" "litteraly" "can" "t"))
        (ebnf-tokenizer "I litteraly can't."))))

  (testing "abbreviation"
    (is
     (= '(("born" "in" "the" "u.s.a."))
        (ebnf-tokenizer "born in the u.s.a."))))

  (testing "brackets"
    (is
     (= '(("hello" "world"))
        (ebnf-tokenizer "[(hello world)]"))))

  (testing "exclamation"
    (is
     (= '(("hello" "world"))
        (ebnf-tokenizer "Hello WORLD!!!!"))))

  (testing "sentence fragment"
    (is
     (= '(("when" "we" "got" "in"))
        (ebnf-tokenizer "when we got in \n \n \n")))))


