(ns io.scotthaleen.concordance.app
  (:require
   [clojure.string :as sz]
   [clojure.tools.cli :refer [parse-opts]]
   [io.scotthaleen.concordance.core :as core]
   [io.scotthaleen.concordance.tokenizer :as tokenizer])
  (:gen-class))


(set! *warn-on-reflection* true)

(def ^:const program "concordance-*-standalone.jar")


(def cli-options
  [["-h" "--help"]])


(defn usage [options-summary]
  (->>
   [(str "Usage: " program " text_file [Options]")
    "Options:"
    options-summary]
   (sz/join \newline)))


(defn parsing-error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (sz/join \newline errors)))


(defn system-exit!
  "Wrap System/exit so it can be redef'd for testing"
  [status]
  (System/exit status))


(defn exit!
  "Exit code helper"
  ([] (exit! 0))
  ([status] (system-exit! status))
  ([status msg]
   (do
     (println msg))
   (system-exit! status)))


(defn -main
  [& args]
  (parse-opts args cli-options)
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)
        text-file (first arguments)]

    (cond
      (:help options) (exit! 0 (usage summary))
      (nil? text-file) (exit! 0 (usage summary))
      errors (exit! 1 (parsing-error-msg errors)))

    (doall
      (println
       (core/->Concordance
        (::tokenizer/default tokenizer/tokenizers)
        (slurp text-file)))
      )))

