(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])

(def content (html/html-resource (java.net.URL. "http://newton.newtonsoftware.com/career/CareerHome.action?clientId=8a8725d048f86d3101490ba21f5d3d16&gnewtonResize=http://www.jamasoftware.com/media/email/GnewtonResize.htm&parentUrl=http%3A%2F%2Fwww.jamasoftware.com%2Fcareers%2F")))
(def entries (html/select content [:li.gnewtonJobNode :a]))

(->> entries
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
