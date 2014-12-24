(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])

(def content (html/html-resource (java.net.URL. "http://jobs.ebaycareers.com/portland/mobile-jobs")))
(def entries (html/select content [:tr]))

(->> entries
    (filter #(not-empty (html/select % [:td :a])))
    (filter #(not-empty (html/select % [:td.location])))
    (filter #(= "Portland, OR" (first (:content (last (html/select % [:td.location]))))))
    (map #(first (html/select % [:a])))
    (map #(array-map
            :title (clojure.string/trim (first (:content %)))
            :url   (str "http://jobs.ebaycareers.com" (-> % :attrs :href))))
    (map #(str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url %))
        "&title="
        (java.net.URLEncoder/encode (:title %))))
    (pmap #(client/post %))
)

