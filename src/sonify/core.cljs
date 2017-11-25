(ns sonify.core
  (:require sonify.sound
;            sonify.keyboard
            sonify.client
            ))

(enable-console-print!)

(println "Yo Babe – This text is printed from src/sonify/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  
)
