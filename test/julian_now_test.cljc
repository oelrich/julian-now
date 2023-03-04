(ns julian-now-test
  (:require [clojure.test :refer [deftest testing is]]
            [julian-now :as jn]))

(deftest unix-epoch-now-ts-test
  (testing "Get an Unix epoch timestamp"
    (is (> (jn/unix-epoch-now-ts) 1677917143977))))

(deftest from-unix-epoch-ts-test
  (testing "Calculate the julian date at some Unix epoch timestamps"
    (is (= (jn/from-unix-epoch-ts 0)
           jn/julian-day-at-unix-epoch))
    (is (= (jn/from-unix-epoch-ts 1677917143977)
           2460007.837314549))))

(deftest now-test
  (testing "Get the current julian date"
    (is (> (jn/now) 2460007.837314549))))

(deftest jd-test
  (testing "Get the julian day number"
    (is (= (jn/jd 2460007.837314549) 2460007))))

(deftest ut-test
  (testing "Get the universal time"
    (is (> (jn/ut 2460007.837314549) 0.837314548))
    (is (< (jn/ut 2460007.837314549) 0.837314549))))
