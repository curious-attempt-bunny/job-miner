(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[clj-http.client :as client]
         '[cheshire.core :as json])

(def content (:body (client/get "http://www.jobscore.com/jobs/monsooncommerce/widget_iframe?parent_url=http%3A%2F%2Fwww.monsooncommerce.com%2Fabout%2Fe-commerce-careers&widget_id=js_widget_iframe_1")))

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