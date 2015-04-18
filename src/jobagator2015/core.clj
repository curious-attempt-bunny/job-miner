(ns jobagator2015.core
    (:require
        [net.cgrand.enlive-html :as enlive]
        [clj-http.client :as client]))

(defn get-html
    [url]
    (enlive/html-resource (java.net.URL. url)))

(def select enlive/select)

(defn- link-to-job
    [stem link]
    (array-map
        :title (clojure.string/trim (first (:content link)))
        :url   (str stem (-> link :attrs :href))))

(def site "http://localhost:5000") ; TODO

(defn- job-to-request
    [job]
    (str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url job))
        "&title="
        (java.net.URLEncoder/encode (:title job))))

(defn submit
    [links stem]
    (->> links
        (map #(link-to-job stem %))
        (map job-to-request)
        (pmap client/post)))
