http://www.elementaltechnologies.com/company/careers/open-positions

(def site "https://frozen-forest-9048.herokuapp.com")
(def site "http://localhost:5000")

(require '[net.cgrand.enlive-html :as html])

(def content (html/html-resource (java.net.URL. "http://hire.jobvite.com/CompanyJobs/Careers.aspx?k=JobListing&c=q6M9VfwG&v=1&jvresize=http://newrelic.com/FrameResize.html")))
(def entries (html/select content [:.joblist :li]))

(->> entries
    (filter #(not-empty (html/select % [:a.jvjoblink])))
    (filter #(= "Portland, OR, United States" (first (:content (last (html/select % [:span]))))))
    (map #(first (html/select % [:a.jvjoblink])))
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





