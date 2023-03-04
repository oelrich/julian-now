(ns julian-now)

(def milli-seconds-in-a-day 86400000)

(def julian-day-at-unix-epoch-ratio
  "The number of julian days (2440587.5) at Unix Timestamp 0. Expressed as a ratio."
  (/ 4881175 2))

(def julian-day-at-unix-epoch
  "The number of julian days at Unix Timestamp 0. Expressed as a floating point number."
  (double julian-day-at-unix-epoch-ratio))

(defn ^:export from-unix-epoch-ts-exact
  "The julian day with universal time for a given Unix Timestamp expressed as a ratio."
  [ts]
  (+   (/ ts milli-seconds-in-a-day)
       julian-day-at-unix-epoch-ratio))

(defn ^:export from-unix-epoch-ts
  "The julian day with universal time for a given Unix Timestamp expressed as a floating point number."
  [ts]
  (double (from-unix-epoch-ts-exact ts)))

(defn unix-epoch-now-ts
  "Current Unix epoch offset."
  []
  #?(:cljs (.now js/Date)
     :clj (System/currentTimeMillis)))

(defn ^:export now
  "Julian day and universal time at the current moment."
  []
  (from-unix-epoch-ts (unix-epoch-now-ts)))

(defn ^:export jd
  "Julian day either now or for a given Unix Timestamp."
  ([] (jd (now)))
  ([jd-ut] (int jd-ut)))

(defn ^:export ut
  "Universal time either now or for a given Unix Timestamp."
  ([] (ut (now)))
  ([jd-ut] (- jd-ut (jd jd-ut))))
