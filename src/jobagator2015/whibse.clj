(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])

(def content (html/html-resource (java.net.URL. "http://www.jobscore.com/jobs2/whibse")))
(def entries (html/select content [:.js-job-listing :a]))

(->> entries
    (map #(first (html/select % [:a])))
    (map #(array-map
            :title (clojure.string/trim (first (:content %)))
            :url   (-> % :attrs :href)))
    (map #(str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url %))
        "&title="
        (java.net.URLEncoder/encode (:title %))))
    (pmap #(client/post %))
)





