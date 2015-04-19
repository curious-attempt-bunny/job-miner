(ns jobagator2015.import
  (:require jobagator2015.core
            jobagator2015.new_relic
            jobagator2015.silicon_florist))

(defn -main
  []
  (do (println "Start")
      (println "New Relic")
      (jobagator2015.new_relic/import)
      (println "Silicon Florist")
      (jobagator2015.silicon_florist/import)
      (println "Stop")))

(-main)