(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])
(require '[clj-http.client :as client])

(def content (html/html-resource (java.net.URL. "http://hire.jobvite.com/CompanyJobs/Careers.aspx?k=JobListing&c=q6M9VfwG&v=1&jvresize=http://newrelic.com/FrameResize.html")))
(def entries (html/select content [:.joblist :tr]))

(->> entries
    (remove #(nil? (html/select % [:a.jvjoblink])))
    (filter #(= "Portland" (first (:content (last (html/select % [:td]))))))
    (map #(first (html/select % [:a.jvjoblink])))
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





