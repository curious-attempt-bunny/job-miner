(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])
(require '[clj-http.client :as client])

(def content (html/html-resource (java.net.URL. "https://cloudability.bamboohr.com/jobs/embed2.php")))
(def entries (html/select content [:li]))

(->> entries
    (remove #(nil? (html/select % [:a])))
    (filter #(= "Portland, Oregon" (first (:content (last (html/select % [:span]))))))
    (map #(first (html/select % [:a])))
    (map #(array-map
            :title (first (:content %))
            :url   (str "https:" (-> % :attrs :href))))
    (map #(str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url %))
        "&title="
        (java.net.URLEncoder/encode (:title %))))
    (pmap #(client/post %))
)





