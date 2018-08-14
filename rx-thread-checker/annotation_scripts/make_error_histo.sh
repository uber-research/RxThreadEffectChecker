# Point this script at the raw output of an Rx Analysis Buck target (i.e. ./buck-out/gen/PATH/TO/YOUR/APP/rx_thread_analysis/rxthreadchecker_processor_output.raw)
# In order to generate a count of total alarms along with a histogram of the top 40 files by alarm count
grep -E "\.java" $1 | sed -E 's/:[0-9]*: (error|warning):.*$/\
TOTAL ALARMS/g' | sort | uniq -c | sort -r | head -n 40
