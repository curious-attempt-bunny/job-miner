(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])

(def content (html/html-resource (java.net.URL. "http://elementaltechnologies.theresumator.com/apply/jobs/")))
(def entries (html/select content [:a.job_title_link]))

(->> entries
    (map #(first (html/select % [:a])))
    (map #(array-map
            :title (clojure.string/trim (first (:content %)))
            :url   (str "http://elementaltechnologies.theresumator.com" (-> % :attrs :href))))
    (map #(str
        site
        "/jobs?url="
        (java.net.URLEncoder/encode (:url %))
        "&title="
        (java.net.URLEncoder/encode (:title %))))
    (pmap #(client/post %))
)





