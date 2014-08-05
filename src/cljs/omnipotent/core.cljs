(ns omnipotent.core
  (:require-macros [omnipotent.macros      :refer [fn->> fn-> <- ann annt annv]]
                   [cljs.core.async.macros :refer [go]])
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


(def initial-app (annt "Initial app-state defined"
                         {:text "Hello World"
                          :errors ["1" "2" "3"]}))


(defn get-new-data
  "Fetch new data from server"
  [app-state data-keyword]
  (ann (str "(get-data app-state " data-keyword ") => nil, fetching")
         (edn-xhr
          {:method :get
           :url (str "/" (name data-keyword) "/")
           :on-complete
           (fn [res]
             (ann (str "Fetch response received for " data-keyword)
                  (if-let [data (get-in res [:response data-keyword])]
                    (om/update! app-state data-keyword data))))
           :on-error
           (fn [{:keys [error]}]
             (annv (str "Failed fetching " data-keyword) [error] nil))})))

(defn get-data
  "Retrieve data from app-state, or fetch it from server if necassary"
  [app-state data-keyword]
  (if-let [data (data-keyword app-state)]
    data
    (get-new-data app-state data-keyword)))


(def app-renderer
  (fn [app owner]
    (om/component (annv "app-renderer[]" [foon]
                       (some->> app
                                :text
                                (dom/h1 nil))))))

(def mise-renderer
  (fn [app owner]
    (om/component (ann "mise-renderer[]"
                       (some->> (get-data app :mises)
                                (map #(dom/li nil (:name %)))
                                (apply dom/ul #js {:className "mises"}))))))


(def error-renderer
  (fn [app owner]
    (om/component (ann "error-renderer[]"
                       (some->> app
                                :errors
                                (map #(dom/li nil %))
                                (apply dom/ul #js {:className "errors"}))))))







;; Execution begins here
(om/root
 app-renderer
 initial-app
 {:target (. js/document (getElementById "app"))})

(om/root
 mise-renderer
 initial-app
 {:target (. js/document (getElementById "mises"))})

(om/root
 error-renderer
 initial-app
 {:target (. js/document (getElementById "errors"))})




(comment
;; BEGIN COMMENTS -- BEGIN COMMENTS -- BEGIN COMMENTS -- BEGIN COMMENTS -- BEGIN COMMENTS -- END COMMENTS --

  (defn show-err-msg [app err-msg]
    (om/transact! app :errors (fn [v]
                                (put! v [:err-msg err-msg])
                                v)))
  




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
