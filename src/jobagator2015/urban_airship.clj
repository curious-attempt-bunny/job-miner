(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[clj-http.client :as client]
         '[cheshire.core :as json])

(def content (:body (client/get "http://urbanairship.com/javascripts/json/jobs.json?0.8499970028642565")))

(def entries (get (json/parse-string content) "jobs"))

(->> entries
    (filter #(= "Portland" (get % "city")))
    (map #(array-map
            :title (get % "title")
            :url   (get % "detail_url")))
     (map #(str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url %))
        "&title="
        (java.net.URLEncoder/encode (:title %))))
     (pmap #(client/post %))
)

