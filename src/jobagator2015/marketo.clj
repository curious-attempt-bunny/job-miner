(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])

(def content (html/html-resource (java.net.URL. "http://marketo.jobs/careers.php")))
(def entries (html/select content [:.Listing :tr]))

(->> entries
    (filter #(not-empty (html/select % [:a])))
    (filter #(= "Portland, OR, United States" (first (:content (last (html/select % [:td]))))))
    (map #(first (html/select % [:a])))
    (map #(array-map
            :title (clojure.string/trim (first (:content %)))
            :url   (str "http://marketo.jobs/careers.php" (-> % :attrs :href))))
    (map #(str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url %))
        "&title="
        (java.net.URLEncoder/encode (:title %))))
    (pmap #(client/post %))
)





