package com.sdtv.rdkremote.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sdtv.rdkremote.databinding.FragmentRemoteLandingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class RemoteLandingFragment : Fragment() {

    private lateinit var binding: FragmentRemoteLandingBinding
    val args: RemoteLandingFragmentArgs by navArgs()

    var ip = String()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRemoteLandingBinding.inflate(layoutInflater)
        ip = args.boxIp
        return binding.root
    }

    override fun onStart() {
        binding.RDKSHELLKEYOK.setOnClickListener {
            lifecycleScope.launch {
                fire(13)
            }
        }

        binding.RDKSHELLKEYUP.setOnClickListener {
            lifecycleScope.launch {
                fire(38)
            }
        }

        binding.RDKSHELLKEYRIGHT.setOnClickListener {
            lifecycleScope.launch {
                fire(39)
            }
        }

        binding.RDKSHELLKEYLEFT.setOnClickListener {
            lifecycleScope.launch {
                fire(37)
            }
        }

        binding.RDKSHELLKEYDOWN.setOnClickListener {
            lifecycleScope.launch {
                fire(40)
            }
        }

        binding.RDKSHELLKEYHOME.setOnClickListener {
            lifecycleScope.launch {
                fire(36)
            }
        }

        super.onStart()
    }

    suspend fun fire(k : Int) = suspendCoroutine<String> { cont ->
        val queue = Volley.newRequestQueue(context)
        val url = "http://$ip:9998/jsonrpc"
        val basePayload = JSONObject()

        basePayload.put("jsonrpc", "2.0")
        basePayload.put("id", k)
        basePayload.put("method", "org.rdk.RDKShell.1.generateKey")
        val keyParams = JSONObject()

        val keyArrays = JSONArray()

        val keyCode1 = JSONObject()
        keyCode1.put("keyCode", k)
        keyCode1.put("modifiers", JSONArray())

        keyArrays.put(keyCode1)
        keyParams.put("keys", keyArrays)

        basePayload.put("params", keyParams)

        val jsonObject: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST, url, basePayload,
            Response.Listener {
                Log.d("RDK_REMOTE", "Response: $it")
            },
            Response.ErrorListener {
                Log.d("RDK_REMOTE", "Error: $it")
            }){}

        queue.add(jsonObject)
    }

}