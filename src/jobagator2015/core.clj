(ns jobagator2015.core
    (:require
        [net.cgrand.enlive-html :as html]
        [clj-http.client :as client]
        [environ.core :refer [env]]))

(defn get-html
    [url]
    (html/html-resource (java.net.URL. url)))

(defn select
    [selector content]
    (html/select content selector))

(defn- link-to-job
    [stem link]
    (array-map
        :title (clojure.string/trim (first (:content link)))
        :url   (str stem (-> link :attrs :href))))

(def site (env :site-url))

(defn- job-to-request
    [job]
    (str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url job))
        "&title="
        (java.net.URLEncoder/encode (:title job))))

(defn submit
    ([links]
        (submit links ""))
    ([links stem]
        (->> links
            (map #(link-to-job stem %))
            (map job-to-request)
            (pmap client/post))))