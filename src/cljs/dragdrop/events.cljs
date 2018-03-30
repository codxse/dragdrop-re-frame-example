(ns dragdrop.events
  (:require [re-frame.core :as rf]
            [dragdrop.db :as db]))

(rf/reg-event-db
  :initialize-db
  (fn  [_ _]
    db/default-db))

(rf/reg-event-db
  :set-order-index
  (fn [db [_ order-index]]
    (assoc db :order-index order-index)))

(rf/reg-event-db
  :set-drag-index
  (fn [db [_ drag-index]]
    (assoc db :drag-index drag-index)))

(rf/reg-event-db
  :set-drag-over
  (fn [db [_ drag-over]]
    (assoc db :drag-over drag-over)))

(rf/reg-event-db
  :drag-over-nothing
  (fn [db _]
    (assoc db :drag-over 9999)))

(rf/reg-event-db
  :clean-drag-n-drop
  (fn [db _]
    (dissoc db :drag-over :drag-index)))

(rf/reg-event-db
  :swap-position
  (fn [db [_ swap-fn position drag-index]]
    (update db :order-index swap-fn position drag-index)))