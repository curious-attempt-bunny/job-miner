(ns jobagator2015.altsrc
    (:require [jobagator2015.core :refer [get-html select submit]]))

(defn -main
    []
    (->
        (get-html "http://www.altsrc.net/Careers")
        (select [:a.fillDivJob])
        (submit "http://www.altsrc.net")))

; (def site "https://frozen-forest-9048.herokuapp.com")
; (def site "http://localhost:5000")

; (require '[net.cgrand.enlive-html :as html])

; (def content (html/html-resource (java.net.URL. "http://www.altsrc.net/Careers")))
; (def entries (html/select content [:a.fillDivJob]))

; (->> entries
;     (map #(array-map
;             :title (clojure.string/trim (first (:content %)))
;             :url   (str "http://www.altsrc.net" (-> % :attrs :href))))
;     (map #(str
;         site
;         "/jobs?url="
;         (java.net.URLEncoder/encode (:url %))
;         "&title="
;         (java.net.URLEncoder/encode (:title %))))
;     (pmap #(client/post %))
; )
