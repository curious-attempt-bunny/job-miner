(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[clj-http.client :as client]
         '[cheshire.core :as json])

(def content (:body (client/get "https://www.jobscore.com/jobs/legitscript/widget_iframe?zebra_stripe=true&list_type=multi&parent_url=https%3A%2F%2Fwww.legitscript.com%2Fabout%2Fcareers%2F&widget_id=js_widget_iframe_1")))

(def job_data (->> (re-seq #"var jobs_data_preload = (.*?});" content) first last))

(def entries (get (json/parse-string job_data) "jobs"))

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