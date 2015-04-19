(ns jobagator2015.silicon_florist
  (:require [jobagator2015.core :refer [get-html select submit]]))

(defn import
  []
  (->> (get-html "http://siliconflorist.com/jobs/")
     (select [:table#wpjb_jobboard :tr])
     (map #(first (select [:td :a] %)))
     (remove nil?)
     (submit)))
