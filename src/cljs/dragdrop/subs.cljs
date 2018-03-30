(ns dragdrop.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  :state
  (fn [db] db))

(rf/reg-sub
  :name
  (fn [db]
    (:name db)))

(rf/reg-sub
  :order-index
  (fn [db]
    (get db :order-index [])))

(rf/reg-sub
  :drag-index
  (fn [db]
    (get db :drag-index)))
