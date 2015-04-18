(def site "http://localhost:5000")
(def site "https://frozen-forest-9048.herokuapp.com")

(require '[net.cgrand.enlive-html :as html])

(def content (html/html-resource (java.net.URL. "http://siliconflorist.com/jobs/")))
(def entries (html/select content [:table#wpjb_jobboard :tr]))

(->> entries
     (map #(first (html/select % [:td :a])))
     (remove nil?)
     (map #(array-map
                :title (first (:content %))
                :url   (-> % :attrs :href)))
     (map #(str
            site
            "/jobs?url="
            (java.net.URLEncoder/encode (:url %))
            "&title="
            (java.net.URLEncoder/encode (:title %))))
     (pmap #(client/post %))
)



