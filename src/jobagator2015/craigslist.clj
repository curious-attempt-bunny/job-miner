(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])
(require '[clj-http.client :as client])

(def content (html/html-resource (java.net.URL. "http://portland.craigslist.org/search/sof")))
(def entries (html/select content [:p.row :a.hdrlnk]))

(->> entries
    (remove nil?)
     (map #(array-map
                :title (first (:content %))
                :url   (str "http://portland.craigslist.org"
                            (-> % :attrs :href))))
     (map #(str
            site
            "/jobs?url="
            (java.net.URLEncoder/encode (:url %))
            "&title="
            (java.net.URLEncoder/encode (:title %))))
     (pmap #(client/post %))
)




