(ns jobagator2015.new_relic
    (:require [jobagator2015.core :refer [get-html select submit]]))

(->> (get-html "http://hire.jobvite.com/CompanyJobs/Careers.aspx?k=JobListing&c=q6M9VfwG&v=1&jvresize=http://newrelic.com/FrameResize.html")
    (select [:.joblist :li])
    (remove #(nil? (select [:a.jvjoblink] %)))
    (filter #(= "Portland, OR, United States" (first (:content (last (select [:span] %))))))
    (map #(first (select [:a.jvjoblink] %)))
    (submit)
)