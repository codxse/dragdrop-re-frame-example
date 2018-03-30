(ns dragdrop.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [dragdrop.core-test]
   [dragdrop.common-test]))

(enable-console-print!)

(doo-tests 'dragdrop.core-test
           'dragdrop.common-test)
