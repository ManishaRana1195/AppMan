(ns test-cljs.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(enable-console-print!)

;; request
(go (let [response (<! (http/get "/all"))]
    )) 


;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello world!"}))




(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
