(ns dragdrop.events
  (:require [re-frame.core :as rf]
            [dragdrop.db :as db]))

(rf/reg-event-db
  :initialize-db
  (fn  [_ _]
    db/default-db))

(rf/reg-event-db
  :init-order-index
  (fn [db [_ order-index]]
    (assoc db :order-index order-index)))

(rf/reg-event-db
  :set-order-index
  (fn [db [_ order-index]]
    (assoc db :order-index order-index)))

(rf/reg-event-db
  :set-drag-index
  (fn [db [_ drag-index]]
    (assoc db :drag-index drag-index)))

(rf/reg-event-db
  :clean-drag-n-drop
  (fn [db _]
    (dissoc db :drag-index :drag-over)))

(rf/reg-event-db
  :set-drag-over-index
  (fn [db [_ index]]
    (assoc db :drag-over index)))

(rf/reg-event-db
  :drag-over-nothing
  (fn [db _]
    (assoc db :drag-over -999999)))

(rf/reg-event-db
  :swap-position
  (fn [db [_ items position drag-index]]
    (let [new-items (remove #{drag-index} items)
          head (take position new-items)
          tail (drop position new-items)
          new-order-index (concat head [new-items] tail)]
      (update db assoc :order-index new-order-index))))