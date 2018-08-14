# Point this script at the raw output of an Rx Analysis Buck target (i.e. ./buck-out/gen/PATH/TO/YOUR/APP/rx_thread_analysis/rxthreadchecker_processor_output.raw)
# In order to generate a histogram of checker errors by type
grep -E "\.java" $1 | grep -oE "\[[a-z\.]*\]" | sort | uniq -c | sort -r
