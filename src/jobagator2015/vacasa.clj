(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[clj-http.client :as client])

(def content (:body (client/get "https://vacasarentals.applicantpro.com/jobs/")))

(def entries (re-seq #"(?s)<a href=\"(.*?)\" class=\"list-group-item.*?<h4>(.*?)</h4>.*?<li>(.*?)</li>" content))

(->> entries
    (map rest)
    (filter #(= "Portland, OR, USA" (last %)))
    (map #(array-map
            :title (second %)
            :url   (first %)))
     (map #(str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url %))
        "&title="
        (java.net.URLEncoder/encode (:title %))))
     (pmap #(client/post %))
)