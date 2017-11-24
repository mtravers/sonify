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

  

