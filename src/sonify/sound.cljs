(ns sonify.sound
  (:require [hum.core :as hum]))


(def ctx (hum/create-context))
(def vco (hum/create-osc ctx :sawtooth))
(def vcf (hum/create-biquad-filter ctx))
(def output (hum/create-gain ctx))

; connect the VCO to the VCF and on to the output gain node
(hum/connect vco vcf output)

(hum/start-osc vco)

(hum/connect-output output)

(hum/note-off output vco 440)

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


  

