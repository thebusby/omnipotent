(ns omnipotent.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [goog.events :as events]
            [cljs.core.async :as async :refer [put! <! chan]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [om-sync.core :refer [om-sync]]
            [om-sync.util :refer [tx-tag edn-xhr]]
            [sablono.core :as html :refer-macros [html]])
  (:import [goog History]
           [goog.history EventType]))

(enable-console-print!)

(def app-state (atom {:text "Hello world!"}))

(om/root
 (fn [app owner]
   (dom/h1 nil (:text app)))
 app-state
 {:target (. js/document (getElementById "app"))})







(comment
;; BEGIN COMMENTS -- BEGIN COMMENTS -- BEGIN COMMENTS -- BEGIN COMMENTS -- BEGIN COMMENTS -- END COMMENTS --





  ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;;
  ;;                                    STEPS TO ENABLE BROWSER REPL
  ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;; ;;

  ;; --------
  ;; STEP ONE
  ;; --------

  ;; Start cider REPL


  ;; --------
  ;; STEP TWO
  ;; --------

  ;; Disable pretty-print in cider REPL
  ;; M-x cider-repl-toggle-pretty-printing


  ;; ----------
  ;; STEP THREE
  ;; ----------

  ;; Run the following in the cider REPL
  (require 'cljs.repl.browser)
  (cemerick.piggieback/cljs-repl
   :repl-env (cljs.repl.browser/repl-env :port 9000))


  ;; ---------
  ;; STEP FOUR
  ;; ---------

  ;; Load up the web page in your browser
  ;; http://localhost:8888


  ;; ---------
  ;; STEP FIVE
  ;; ---------

  ;; Test connectivity
  (.alert js/window "Test")



;; END COMMENTS -- END COMMENTS -- END COMMENTS -- END COMMENTS -- END COMMENTS -- END COMMENTS --
  )
