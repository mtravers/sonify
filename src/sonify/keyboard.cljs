(ns sonify.keyboard
  (:require [hum.core :as hum]
            [dommy.core :as dommy :refer-macros [sel sel1]]
            [hipo.core :as hipo])
  )

 
(def ctx (hum/create-context))
(def vco (hum/create-osc ctx :square))
(def vcf (hum/create-biquad-filter ctx))
(def output (hum/create-gain ctx))

(hum/connect vco vcf)
(hum/connect vcf output)

(hum/start-osc vco)

(hum/connect-output output)

(defn note-num-to-frequency [note-num]
  (let [expt-numerator (- note-num 49)
        expt-denominator 12
        expt (/ expt-numerator expt-denominator)
        multiplier (.pow js/Math 2 expt)
        a 440]
  (* multiplier a)))

(defn note-on [note-num]
  (let [freq (note-num-to-frequency note-num)]
    (hum/note-on output vco freq)))

(defn note-off []
  (hum/note-off output))

(defn keyboard-click-handler [event note-num]
  (let [keyboard-key (.-target event)
        freq (note-num-to-frequency note-num)]
    (hum/note-on output vco freq)))

;; would look better with stylesheet, but it works
(defn create-keyboard [keyboard]
  (doseq [i (range 40 53)]
    (let [keyboard-note (hipo/create
                         [:li {:id (str "note-" i)
                               :class (if (some #{i} [41 43 46 48 50]) ;; black keys
                                        "accidental" "")}
                          (str i)
                          ])]
      (dommy/append! keyboard keyboard-note)
      (dommy/listen! keyboard-note :mousedown #(keyboard-click-handler % i))
      (dommy/listen! keyboard-note :mouseup note-off)))
  (dommy/listen! keyboard :mouseup note-off)
  (dommy/listen! keyboard :mouseleave note-off))

(defn setup []
  (let [keyboard (sel1 "#keyboard ul")]
    (create-keyboard keyboard)))

(set! (.-onload js/window) setup)
